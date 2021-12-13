#!/usr/bin/env python3

from os import read
from functools import reduce

f = open("sample.txt","r")
sample = [[a.split(' ') for a in x.replace('\n','').split(' | ')] for x in f.readlines()]

f = open("input.txt","r")
puzzle = [[a.split(' ') for a in x.replace('\n','').split(' | ')] for x in f.readlines()]

# part 1
def part1(input):
    def process_segment(input):
        return [ len(x) for x in input ]
    count = 0
    for r in input:
        signal = [x for x in process_segment(r[0]) if x in (2,4,3,7)]
        output = [x for x in process_segment(r[1]) if x in (2,4,3,7)]
        count = count + len(output)
    return count

print ("Part 1")
print(part1(sample))
print(part1(puzzle))

print ('Part 2')
def sortString(input):
    l = list(input)
    l.sort()
    return ''.join(l)

def part2(input):
    code_len = { 2: 1, 4: 4, 3: 7, 7: 8 }
    results = []
    for r in input:
        mapping = {}
        (readings,output) = r
        # do the things we know
        for reading in readings:
            reading = sortString(reading)
            if len(reading) in code_len:
                # know 1, 4, 7, 8
                mapping[reading] = code_len[len(reading)]
                mapping[code_len[len(reading)]] = reading
        # do everything else
        for reading in readings:
            reading = sortString(reading)
            if len(reading) == 5:
                # could be 2, 3, 5
                if 1 in mapping:
                    one = set(mapping[1])
                    letters = set(reading)
                    if len(one.intersection(letters))== 2:
                        mapping[reading] = 3
                        mapping[3] = reading
                    elif 4 in mapping:
                        four = set(mapping[4])
                        letters = set(reading)
                        if len(letters.intersection(four)) == 3:
                            mapping[reading] = 5
                            mapping[5] = reading
                        else:
                            mapping[reading] = 2
                            mapping[2] = reading
            elif len(reading) == 6:
                # could be 0, 6, 9
                if 1 in mapping:
                    one = set(mapping[1])
                    letters = set(reading)
                    if len(letters.intersection(one)) != 2:
                        mapping[reading] = 6
                        mapping[6] = reading
                    elif 4 in mapping:
                        four = set(mapping[4])
                        letters = set(reading)
                        if len(letters.intersection(four)) == 4:
                            mapping[reading] = 9
                            mapping[9] = reading
                        else:
                            mapping[reading] = 0
                            mapping[0] = reading
        row_result = []
        for reading in output:
            reading = sortString(reading)
            if reading in mapping:
                row_result.append(mapping[reading])
            else:
                row_result.append(-1)
        res = reduce(lambda x,y: x*10+y, row_result)
        results.append(res)
    return sum(results)

print (part2(sample))
print (part2(puzzle))