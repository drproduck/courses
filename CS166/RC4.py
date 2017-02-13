def swap(list, i, j):
    a =i/16
    b= i%16
    temp = list[a][b]
    c = j/16
    d = j%16
    list[a][b] = list[c][d]
    list[c][d] =  temp


key_length = input()
stream_length = input()


key = []*key_length
stream = []*stream_length

S = [[]*16] * 16
K = [] * 256

for i in range(0, 256):
    a = i/16
    b = i%16
    S[a][b] = i
    stream[i] = key[i%N]

j=0
for i in range(0, 256):

    j = (j + S[i] + K[i]) % 256
    swap(S, i, j)

i = 0
j = 0

i = (i+1) %256
j = (j+S[i])%256
swap(S, i, j)
t = (S[i] + S[j])%256
keystreamByte = S[t]



