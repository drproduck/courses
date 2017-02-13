import math
with open("/home/drproduck/Desktop/alice.bmp", "rb") as file:
    text = file.read(1)
print(text)


key = input()
print(type(key))

K0 = int(key[0], 16)
K1 = int(key[1], 16)
K2 = int(key[2], 16)
K3 = int(key[3], 16)

plaintext = input()
L = int(plaintext[0], 16)
R = int(plaintext[1], 16)

delta = 0x9e3779b9
sum = 0
for i in range(0,32):
    sum += delta
    L += ((R << 4) + K0) ^ (R + sum) ^ ((R >> 5) + K1)
    R += ((L << 4) + K2) ^ (L +sum) ^ ((L >> 5) + K3)

ciphertext = str(L)+str(R)


