#!/usr/bin/env python3

sample = [
    "forward 5",
    "down 5",
    "forward 8",
    "up 3",
    "down 8",
    "forward 2"
]

f = open("input.txt","r")
puzzle = [ x for x in f.readlines()]

# Part 1

import enum
class Instruction(enum.Enum):
    Forward = 1
    Down = 2
    Up = 3

    def parse(string):
        lex = string.split(' ')
        command = { 'forward' : Instruction.Forward, 'up' : Instruction.Up, 'down' : Instruction.Down }[lex[0]]
        return (command, int(lex[1]))

def nextLocation(location, instruction):
    command = {
        Instruction.Forward : lambda l,d: (l[0] + d, l[1]),
        Instruction.Up      : lambda l,d: (l[0], l[1] - d),
        Instruction.Down    : lambda l,d: (l[0], l[1] + d)
        }
    return command[instruction[0]](location, instruction[1])

def part1(instructions):
    location = (0,0)   
    for i in instructions:
        location = nextLocation(location, Instruction.parse(i))
    return location

print ("Part 1")
location = part1(sample)
print ("Sample ", location)
print ("Sample ", location[0] * location[1])

location = part1(puzzle)
print ("Part 1 ", location)
print ("Part 1 ", location[0] * location[1])

print ("Part 2")

def nextLocation2(location, instruction):
    command = {
        Instruction.Forward : lambda l,d: (l[0] + d, l[1] + l[2] * d, l[2]),
        Instruction.Up      : lambda l,d: (l[0], l[1], l[2] - d),
        Instruction.Down    : lambda l,d: (l[0], l[1], l[2] + d)
        }
    return command[instruction[0]](location, instruction[1])

def part2(instructions):
    location = (0,0,0)
    for i in instructions:
        location = nextLocation2(location, Instruction.parse(i))
    return location

location = part2(sample)
print ("Sample ", location)
print ("Sample ", location[0] * location[1])

location = part2(puzzle)
print ("Part 2 ", location)
print ("Part 2 ", location[0] * location[1])
