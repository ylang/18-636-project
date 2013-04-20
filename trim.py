#! /usr/bin/env python

fin = open('./pop.txt', 'r')
fout = open('./popular.txt', 'w+')

for i in range (0, 1000):
    if i != 1000:
        fout.write('http://' + fin.readline()[4:])
    else:
        fout.write('http://' + fin.readline())
