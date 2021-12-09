#!/usr/bin/env python3

from functools import reduce


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
    
    def basin_extent(self,x,y, current= []):
        if self.depth(x,y) == 9:
            return current
        if (x,y) in current:
            return current
        current.append((x,y))
        if x > 0 and (x-1,y) not in current:
            current = self.basin_extent(x-1,y,current)
        if x < len(self.map[0])-1 and (x+1,y) not in current:
            current = self.basin_extent(x+1,y,current)
        if y > 0 and (x,y-1) not in current:
            current = self.basin_extent(x,y-1,current)
        if y < len(self.map)-1 and (x,y+1) not in current:
            current = self.basin_extent(x,y+1,current)
        return current
    
    def visited(self,basins):
        return [item for subl in basins for item in subl]

    def part2(self):
        basins = []
        for y in range(0,len(self.map)):
            for x in range(0,len(self.map[0])):
                if self.depth(x,y) != 9 and (x,y) not in self.visited(basins):
                    r = self.basin_extent(x,y, [])
                    basins.append(r)
        basin_sizes = [len(x) for x in basins]
        basin_sizes.sort()
        return reduce(lambda x,y: x * y, basin_sizes[-3:])

map_sample = Map(sample)
print (map_sample.part1())

map = Map(puzzle)
print (map.part1())

# Part 2
print ('Part 2')
print (map_sample.part2())
print (map.part2())
