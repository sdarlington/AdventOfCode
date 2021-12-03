#!/usr/bin/env python3

sample = [
    "00100",
    "11110",
    "10110",
    "10111",
    "10101",
    "01111",
    "00111",
    "11100",
    "10000",
    "11001",
    "00010",
    "01010"
]

f = open("input.txt","r")
puzzle = [ x.replace('\n','') for x in f.readlines()]

# Part 1

def digitCount(list, digit):
    digits = [x[digit] for x in list]
    zeros = len([x for x in digits if x == '0'])
    ones =  len([x for x in digits if x == '1'])
    return (zeros,ones)

def rangeCount(list):
    for d in range(0,len(list[0])):
        counts = digitCount(list,d)
        if counts[0] > counts[1]:
            yield 0
        else:
            yield 1
        
def reverseDigits(list):
    for d in list:
        if d == 0:
            yield 1
        else:
            yield 0

def part1(list):
    gamma = [x for x in rangeCount(list)]
    epsilon = [x for x in reverseDigits(gamma)]
    g = [ str(x) for x in gamma]
    e = [ str(x) for x in epsilon]
    return int(''.join(g),2) * int(''.join(e),2)

print(part1(sample))
print (part1(puzzle))

# part 2

print ("Part 2")

def part2test(list, t):
    for posn in range(0,len(list[0])):
        if len(list) > 1:
            ranges = [x for x in digitCount(list, posn)]
            if t(ranges[0], ranges[1]):
                filter = '0'
            else:
                filter = '1'
            list = [x for x in list if x[posn] == filter]
    return list[0]

def oxygen(list):
    return part2test(list, lambda x,y: x > y)

def co2(list):
    return part2test(list, lambda x,y: x <= y)

def part2(list):
    return int(oxygen(list),2) * int(co2(list),2)

print (part2(sample))
print (part2(puzzle))
