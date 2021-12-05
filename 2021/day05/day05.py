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

    def points(self, diagonals = False):
        if self.start[0] == self.end[0]:
            for x in range(min(self.start[1],self.end[1]), max(self.start[1], self.end[1]) + 1):
                yield (self.start[0], x)
        elif self.start[1] == self.end[1]:
            for x in range(min(self.start[0],self.end[0]), max(self.start[0], self.end[0]) + 1):
                yield (x, self.start[1])  
        elif diagonals:
            if self.end[0] > self.start[0]:
                dx = 1
            else:
                dx = -1
            if self.end[1] > self.start[1]:
                dy = 1
            else:
                dy = -1
            loc = self.start
            yield (loc[0], loc[1])
            while loc[0] != self.end[0] and loc[1] != self.end[1]:
                loc = [ loc[0] + dx, loc[1] + dy ]
                yield (loc[0], loc[1])

class World:
    def __init__(self,diagnoals = False):
        self.world = {}
        self.diagnoals = diagnoals

    def plotVent(self,vent):
        for p in vent.points(self.diagnoals):
            if p in self.world:
                c = self.world[p]
            else:
                c = 0
            self.world[p] = c + 1

    def recordVents(self, vents):
        for v in vents:
            self.plotVent(v)

    def print(self):
        maxx = max([ x[0] for x in self.world])
        maxy = max([ x[1] for x in self.world])
        for y in range(0, maxy+1):
            for x in range(0, maxx + 1):
                if (x,y) in self.world:
                    print(self.world[(x,y)], end='')
                else:
                    print('.', end='')
            print()

    def output(self, limit = 0):
        return [ x for x in self.world if self.world[x] > limit]

print("Part 1")
w = World()
w.recordVents([Vent(x) for x in sample])
print("Sample: ", len(w.output(1)))

w = World()
w.recordVents([Vent(x) for x in puzzle])
print("Part 1: ", len(w.output(1)))

print("Part 2")
w = World(True)
w.recordVents([Vent(x) for x in sample])
print("Sample: ", len(w.output(1)))

w = World(True)
w.recordVents([Vent(x) for x in puzzle])
print("Part 2: ", len(w.output(1)))
