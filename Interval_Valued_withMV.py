dataset = [[0.11,0.43], [0.5,0.85],  [0.4,0.8], [0.6, 0.8],  [0.4,0.6], [0.54, 0.74], [0.8, 0.9], [0.4, 0.5], [1,1]]
label = ['Yes', 'No', 'Yes', 'Yes', 'No', 'No', 'Yes', 'No', 'Yes']

output = []
neg = []
pos = []
Positive_count = 0
Negative_count = 0

for x in dataset:
    if (x[0] <0 or x[1] > 1):
        print('Probablistic ranges entered are incorrect')
        break

    if (x[0] < 0.5 and x[1] < 0.5):
        output.append('Negative')

    if (x[0] > 0.5 and x[1] > 0.5):
        output.append('Positive')

    if (x[0] == 0.5 and x[1] > 0.5) :
        output.append('Semi-Positive')

    if (x[0] < 0.5 and x[1] == 0.5):
        output.append('Semi-Negative')

    if (x[0] < 0.5 and x[1] > 0.5):
        neg = [x[0], 0.5]
        pos = [0.5, x[1]]

        if((neg[1] - neg[0]) > (pos[1] - pos[0])):
            output.append('Weakly Negative')
        else:
            output.append('Weakly Positive')   
    
    if (x[0] == 0.5 and x[1] == 0.5):
        output.append('Neither')

print(output)

for x in output:
    if ((x == 'Positive') or (x == 'Semi-Positive') or (x == 'Weakly-Positive')):
        Positive_count += 1
    if ((x == 'Negative') or (x == 'Semi-Negative') or (x == 'Weakly-Negative')):
        Negative_count += 1

if (Positive_count > Negative_count):
    print('According to the Majority Vote the label is Yes')
else:
    print('According to the Majority Vote the label is No')

