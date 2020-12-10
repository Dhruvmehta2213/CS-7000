import ceka.consensus.AdaptiveWeightedMajorityVote;
import ceka.consensus.MajorityVote;
import ceka.consensus.ds.DawidSkene;
import ceka.consensus.glad.GLADWraper;
import ceka.consensus.kos.KOS;
import ceka.consensus.square.SquareIntegration;
import ceka.converters.FileLoader;
import ceka.converters.FileSaver;
import ceka.core.Dataset;
import ceka.noise.ClassificationFilter;
import ceka.noise.SelfTrainCorrection;
import ceka.simulation.ExampleWorkersMask;
import ceka.utils.DatasetManipulator;
import ceka.utils.PerformanceStatistic;
import weka.classifiers.Classifier;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.NormalizedPolyKernel;

public class Test{	
	public void myMethod() throws Exception {
		String responsePath = "I:\\CEKASPACE\\Ceka-Trial\\data\\real-world\\income94crowd\\income94.response.txt";
		
		String goldPath = "I:\\CEKASPACE\\Ceka-Trial\\data\\real-world\\income94crowd\\income94.gold.txt";
		
		String arffPath = "I:\\CEKASPACE\\Ceka-Trial\\data\\real-world\\income94crowd\\Income94.arff";
		
		Dataset dataset = FileLoader.loadFile(responsePath, goldPath, arffPath);
		
		/* For MV Algorithm
		MajorityVote mv = new MajorityVote();
		
		mv.doInference(dataset);
		*/
		
		/* For Adaptive Weighted Majority Voting Algorithm
		AdaptiveWeightedMajorityVote wv = new AdaptiveWeightedMajorityVote();
		
		wv.doInference(dataset);
		*/
		
		/* For DS Algorithm
		DawidSkene ds = new DawidSkene(50);
		 
		ds.doInference(dataset);
		*/
		
		/* For KOS Algorithm
		KOS kos = new KOS(10);
		
		kos.doInference(dataset);
		*/
		
		// For RY Algorithm
		SquareIntegration si = new SquareIntegration("I:\\CEKASPACE\\Ceka-Trial\\Crowdsourcing\\src");
		
		si.doInference(dataset, SquareIntegration.methodRYBinary);
		//
		
		
		// noise filtering with the CF algorithm
		Classifier [] classifiers = new Classifier[1];
		
		classifiers[0] = new SMO();
		
		/*
		Class<?> m_class = Class.forName("weka.classifiers.functions.SMO");
		
		classifiers[0] = (Classifier) m_class.newInstance();
		
		((SMO)classifiers[0]).setKernel(new NormalizedPolyKernel());
		*/
		
		ClassificationFilter cf = new ClassificationFilter(10);
		
		//Call the filterNoise function to separate the data
		cf.filterNoise(dataset, classifiers);
		
		// Initialize the dataset variables for clean and noise data
		Dataset noise_data = null; 
		
		Dataset clean_data = null;
		
		//Extract the clean and noise data
		clean_data = cf.getCleansedDataset();
		
		noise_data = cf.getNoiseDataset();
		
		// STC class with the required parameters and 
		SelfTrainCorrection stc = new SelfTrainCorrection(noise_data, clean_data, 1.0);
		
		// The classifier to be used to correct the data which has been learned
		stc.correction(classifiers[0]);
		
		//Merge the data once labels have been corrected
		DatasetManipulator.addAllExamples(noise_data, clean_data); 
		
		//Call the class for obtaining the performance and parse the merged data to it
		PerformanceStatistic reporter = new PerformanceStatistic();
		reporter.stat(noise_data);
		
		//Print the final statistics needed to show the performance
		System.out.println("For Adaptive Weighted Majority Voting Algorithm with Income dataset:");
		
		System.out.println("PLAT accuracy: " + reporter.getAccuracy() + "\n" 
		+ " Roc Area: " + reporter.getAUC() + "\n" + " Recall: " 
		+ reporter.getRecallBinary() + "\n" + " Precision: " 
		+ reporter.getPresicionBinary()+ "\n" + " F1:" + reporter.getF1MeasureBinary());
	}
	
	public static void main(String[] args) throws Exception {
		Test mytest = new Test();
		mytest.myMethod();
	  }
}