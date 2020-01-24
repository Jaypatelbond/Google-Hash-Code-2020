f = open("d_quite_big.in","r")
l = list(map(int,f.readline().split(" ")))
m = list(map(int,f.readline().split(" ")))
max = l[0]
n = l[1]
sum = 0
count = 0
ls = []

for i in range(n-1,-1,-1):
 if(sum + m[i] <= max):
        sum = sum + m[i]
        count += 1
        ls.insert(0,i)


f = open("d_quite_big.out","w")
f.write(str(count)+'\n')
for i in ls:
    f.write(str(i)+" ")
f.close

