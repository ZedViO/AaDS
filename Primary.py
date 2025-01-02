n = int(input())                                   
prime = [ ]                                         
for x in range(2, n + 1) :                    
    for y in range(2, ceil(x / 2) + 1) :   
        if not x % y :                              
            break
    else:                                              
         prime.append(x)        
     
print(prime)