#!/usr/bin/env python3

f = open("sample.txt","r")
sample = [[int(a) for a in x.replace('\n','')] for x in f.readlines()]

f = open("input.txt","r")
puzzle = [[int(a) for a in x.replace('\n','')] for x in f.readlines()]

# Part 1
print ('Part 1')

class Map:
    def __init__(self,input):
        self.map = input

    def depth(self,x,y):
        return self.map[y][x]

    def surrounding(self,x,y):
        r = []
        if x > 0:
            r.append(self.depth(x-1,y))
        if x < len(self.map[0])-1:
            r.append(self.depth(x+1,y))
        if y > 0:
            r.append(self.depth(x,y-1))
        if y < len(self.map)-1:
            r.append(self.depth(x,y+1))
        return r
    
    def lowest_points(self):
        r = []
        for x in range(0,len(self.map[0])):
            for y in range(0,len(self.map)):
                depth = self.depth(x,y)
                other = min(self.surrounding(x,y))
                if depth < other:
                    r.append(depth)
        return r

    def part1(self):
        return sum([ x+1 for x in  self.lowest_points() ])

map = Map(sample)
print (map.part1())
map = Map(puzzle)
print (map.part1())