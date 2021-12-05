#!/usr/bin/env python3

f = open("sample.txt","r")
sample = [ x.replace('\n','') for x in f.readlines()]

f = open("input.txt","r")
puzzle = [ x.replace('\n','') for x in f.readlines()]


# part 1
class Vent:
    def __init__(self):
        self.start = []
        self.end = []
    def __init__(self,input):
        elements = input.split(' -> ')
        self.start = [ int(x) for x in elements[0].split(',') ]
        self.end   = [ int(x) for x in elements[1].split(',') ]
    def print(self):
        print(self.start, " -> ", self.end)
    def points(self):
        if self.start[0] == self.end[0]:
            for x in range(min(self.start[1],self.end[1]), max(self.start[1], self.end[1]) + 1):
                yield (self.start[0], x)
        elif self.start[1] == self.end[1]:
            for x in range(min(self.start[0],self.end[0]), max(self.start[0], self.end[0]) + 1):
                yield (x, self.start[1])            

class World:
    def __init__(self):
        self.world = {}
    def plotVent(self,vent):
        for p in vent.points():
            if p in self.world:
                c = self.world[p]
            else:
                c = 0
            self.world[p] = c + 1
    def recordVents(self, vents):
        for v in vents:
            self.plotVent(v)
    def output(self, limit = 0):
        return [ x for x in self.world if self.world[x] > limit]

print("Part 1")
w = World()
w.recordVents([Vent(x) for x in sample])
print("Sample: ", len(w.output(1)))

w = World()
w.recordVents([Vent(x) for x in puzzle])
print("Part 1: ", len(w.output(1)))
