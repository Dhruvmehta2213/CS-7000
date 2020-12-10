import statistics

lower_limit = []
upper_limit = []
combined_list = []

final_pdf = []

probablity_of_matching = 0
probablity_of_mis_matching = 0

dataset = [[0.11,0.43], [0.10,0.35],  [0.2,0.3], [0.6, 0.8],  [0.4,0.6]]
label = ['Yes', 'No', 'Yes', 'Yes', 'No']

segments = []

for x in dataset:
    lower_limit.append(x[0])
    upper_limit.append(x[1])
    combined_list.append(x[0])
    combined_list.append(x[1])

combined_list.sort()

#print(combined_list)

seg_length = len(combined_list)

Segment = [None] * (seg_length-1)

for i in range(0,seg_length-1):
    segments.append([combined_list[i],combined_list[i+1]])

#print(segments)

mean_dataset = [statistics.mean(lower_limit), statistics.mean(upper_limit)]

envelope_dataset = [min(lower_limit), max(upper_limit)]

if(max(lower_limit) > min(upper_limit)):
    core_dataset = [0]
else:
    core_dataset = [max(lower_limit), min(upper_limit)]

if(core_dataset != [0]):
    mode_dataset = core_dataset

#print(mean_dataset)
#print(envelope_dataset)
#print(core_dataset)

#For the pdf we choose the uniform distribution
# The formula for uniform distribution is as follows: 1/(lower_limit(xi) - upper_limit(xi))

for x in dataset:
    x_low = x[0]
    #print(x_low)

    x_high = x[1]
    #print(x_high)

    pdf = 1/(x_high-x_low)
    #print(pdf)

    j = combined_list.index(x_low)
    k = combined_list.index(x_high)

    #print(k)

    for l in range(j,k):
        if(Segment == []):
            for r in range(j,k):
                Segment.append(pdf)
            break
        else:
            if(Segment[l] == None):
                Segment[l] = pdf
            else:
                Segment[l] = Segment[l] + pdf

#print(Segment)

for x in Segment:
    final_pdf.append(x*(1/(len(dataset)*10)))

print(final_pdf)

for i in range(0,len(segments)):
    print(f'The Probablity distribution of the segment {segments[i]} is: {final_pdf[i]}')

threshold = 0.25

for x in final_pdf:
    if x >= threshold:
        probablity_of_matching += x
    else:
        probablity_of_mis_matching += x

print(probablity_of_matching)
