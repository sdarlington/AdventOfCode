#!/usr/bin/env ruby

require 'set'

def day8(f)
  File.readlines(f).map { |x| x.chomp.split('').map { |x| x.to_i } }
end


def getTree(map,x,y)
  map[y][x]
end

def getMaxX(map)
  map[0].size
end

def getMaxY(map)
  map.size
end

def part1(f)
  game = day8(f)
    
  visible = Set.new()
    
  for r in (0...getMaxY(game)) do
    height = -1
    for c in (0...getMaxX(game)) do
      newTree = getTree(game,c,r)
      if newTree > height
        visible << [c,r]
        height = newTree
      end
    end
  end

  for c in (0...getMaxX(game)) do
    height = -1
    for r in (0...getMaxY(game)) do
      newTree = getTree(game,c,r)
      if newTree > height
        visible << [c,r]
        height = newTree
      end
    end
  end

  for r in (getMaxY(game)-1).downto(0) do
    height = -1
    for c in (getMaxX(game)-1).downto(0) do
      newTree = getTree(game,c,r)
      if newTree > height
        visible << [c,r]
        height = newTree
      end
    end
  end

  for c in (getMaxX(game)-1).downto(0) do
    height = -1
    for r in (getMaxY(game)-1).downto(0) do
      newTree = getTree(game,c,r)
      if newTree > height
        visible << [c,r]
        height = newTree
      end
    end
  end
  
  puts visible.size
  
end

def score(grid, x, y, xs, ys)
  currX = x
  currY = y
  maxX = getMaxX(grid)
  maxY = getMaxY(grid)
  
  height = getTree(grid, currX, currY)
  c = 0
  while true do;
    currX = xs.call(currX)
    currY = ys.call(currY)
    
    break if currX < 0 or currX == maxX or currY < 0 or currY == maxY
    
    newHeight = getTree(grid, currX, currY)
    c = c + 1
    if newHeight >= height
      break
    end
  end
  c
end

def scoreTree(grid, x, y)
  score(grid, x, y, lambda { |x| x     }, lambda { |y| y - 1 }) *
  score(grid, x, y, lambda { |x| x - 1 }, lambda { |y| y     }) *
  score(grid, x, y, lambda { |x| x     }, lambda { |y| y + 1 }) *
  score(grid, x, y, lambda { |x| x + 1 }, lambda { |y| y     })
end

def part2(f)
  grid = day8(f)
            
  puts scoreTree(grid, 2,1)
  puts scoreTree(grid, 2,3)

  best_score = 0
  for x in 0...getMaxX(grid) do
    for y in 0...getMaxY(grid) do
      s = scoreTree(grid,x,y)
      if s > best_score
        best_score = s
      end
    end
  end
  puts best_score

end

puts "Part 1"
part1("sample.txt")
part1("input.txt")

puts "Part 2"
part2("sample.txt")
part2("input.txt")