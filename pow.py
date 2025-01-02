a = int(input())                                  
n = str(bin(int(input())))[2:][::-1]        
res = 1                                              

for i in range(len(n)):                        
    if n[i] == "1":
        res *= a
    a *= a
print(res)