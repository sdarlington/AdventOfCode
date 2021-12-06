#!/usr/bin/env python3

sample = [ 3,4,3,1,2 ]

f = open("input.txt","r")
puzzle = [[int(a) for a in x.replace('\n','').split(',')] for x in f.readlines()][0]

# part 1
def nextDay(input, createFish = 7, newFish = 2):
    for fish in input:
        fish = fish - 1
        if fish < 0:
            fish = fish + createFish
            yield createFish + newFish - 1
        yield fish

def days(input, days):
    # print ('Initial state: ', input)
    for d in range(0,days):
        input = [x for x in nextDay(input)]
        # print ('After ', d + 1, ' days: ', input)
    return input

print ('Part 1')
print (len(days(sample,18)))
print (len(days(sample,80)))
print (len(days(puzzle,80)))
