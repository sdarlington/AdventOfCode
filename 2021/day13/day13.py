#!/usr/bin/env python3

f = open("sample.txt","r")
sample = [ x.replace('\n','') for x in f.readlines()]

f = open("input.txt","r")
puzzle = [ x.replace('\n','') for x in f.readlines()]

# part 1
class Paper:
    def __init__(self,input):
        self.grid = {}
        self.instructions = []
        for l in input:
            coords = l.split(',')
            if len(coords) == 2:
                self.grid[(int(coords[0]),int(coords[1]))] = 1
            elif l.startswith('fold along'):
                (dim,location) = l.split(' ')[2].split('=')
                self.instructions.append((dim,int(location)))

    def printGrid(self):
        for y in range(0,self.maxY() + 1):
            for x in range(0,self.maxX() +1):
                if (x,y) in self.grid:
                    print('#', end='')
                else:
                    print('.', end='')
            print()
    
    def printInstructions(self):
        print(self.instructions)

    def maxX(self):
        return max([ x[0] for x in self.grid])

    def maxY(self):
        return max([ x[1] for x in self.grid])

    def flip(self,dim,loc):
        grid = {}
        if dim == 'y':
            y_start = loc - 1
            y_end   = -1
            y_step  = -1
            x_start = 0
            x_end   = self.maxX() + 1
            x_step  = 1
        else:
            y_start = 0
            y_end   = self.maxY() + 1
            y_step  = 1
            x_start = loc - 1
            x_end   = -1
            x_step  = -1
        for y in range(y_start,y_end,y_step):
            for x in range(x_start,x_end,x_step):
                if dim == 'y':
                    flipped = (x, loc-y+loc)
                else:
                    flipped = (loc-x+loc,y)
                if (x,y) in self.grid and flipped in self.grid:
                    grid[(x,y)] = 2
                elif (x,y) in self.grid or flipped in self.grid:
                    grid[(x,y)] = 1
        self.grid = grid

    def part1(self):
        (dim,loc) = self.instructions[0]
        self.flip(dim,loc)
        return len(self.grid)

print ('Part 1')
sample_paper = Paper(sample)
print(sample_paper.part1())

puzzle_paper = Paper(puzzle)
print(puzzle_paper.part1())
