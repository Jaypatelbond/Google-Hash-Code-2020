# INPUT File Read
f = open("c_medium.in","r")
l = list(map(int,f.readline().split(" ")))
w = list(map(int,f.readline().split(" ")))
tot = l[0]
n = l[1]


count = 0
maxls = []
ls = [[0 for i in range (tot+1)] for j in range(n+1)]
combo = [[0 for i in range (tot+1)] for j in range(n+1)]

# Creating 2D list for Knapsack
for i in range(1,n+1):
    for j in range (1,tot+1):

        temp1 = ls[i-1][j]
        temp2 = ls[i-1][j-w[i-1]] + w[i-1]

        if j >= w[i-1] and temp2 > temp1 :
            ls[i][j] = temp2
            combo[i][j] = 1
            
        else :            
            ls[i][j] = temp1

# for i in ls:    # Knapsack 2D list
#     print(i)
# print()
# for i in combo:    # Knapsack Seq list
#     print(i)

# Maximum value that can be achieved
maxval = ls[n][tot]
print("\n Max value achieved = ",maxval,"\n")

# Finding sequential combination
# for elements to give max val

# for i in range (n,0,-1):
#     k=0
#     for j in range (1,tot+1):
#         if maxval == ls[i][j]:
#             if ls[i][j] != ls[i-1][j]:
#                 count += 1
#                 maxls.insert(0,i-1)
#                 maxval -= w[i-1]
#             break

for i in range (n,0,-1):
    if combo[i][tot] is 1:
        count += 1
        maxls.insert(0,i-1)
        tot -= w[i-1]

print(count)    # no of elements required for max val    
print(maxls)    # sequential combo for max val

# OUTPUT File Write
# f = open("e_also_big.out","w")
# f.write(str(count)+'\n')
# for i in maxls:
#     f.write(str(i)+" ")
# f.close