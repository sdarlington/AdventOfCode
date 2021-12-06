#!/usr/bin/env python3

sample = [ 3,4,3,1,2 ]

f = open("input.txt","r")
puzzle = [[int(a) for a in x.replace('\n','').split(',')] for x in f.readlines()][0]

# part 1
def nextDay(input, createFish = 7, newFish = 2):
    output = {}
    for fish in input:
        new_fish = fish - 1
        if new_fish < 0:
            new_fish = new_fish + createFish
            if createFish + newFish -1 in output:
                v = output[createFish + newFish -1] + input[fish]
            else:
                v = input[fish]
            output[createFish + newFish - 1] = v
        if new_fish in output:
            v = output[new_fish] + input[fish]
        else:
            v = input[fish]
        output[new_fish] = v
    return output

def countFish(input):
    return sum([input[x] for x in input ])

def days(input, days):
    pinput = {}
    for f in input:
        if f in pinput:
            x = pinput[f] + 1
        else:
            x = 1
        pinput[f] = x
    for d in range(0,days):
        pinput = nextDay(pinput)
    return countFish(pinput)

print ('Part 1')
print (days(sample,18))
print (days(sample,80))
print (days(puzzle,80))

# print ('Part 2')
print (days(sample,256))
print (days(puzzle,256))
