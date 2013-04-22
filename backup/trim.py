#! /usr/bin/env python

fin = open('./pop.txt', 'r')
fout = open('./popular.txt', 'w+')

for i in range(0, 1000000):
    line = fin.readline()
    split_line = line.split(',')[-1]
    url = 'http://' + split_line
    fout.write(url)
