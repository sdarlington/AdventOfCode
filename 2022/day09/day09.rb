#!/usr/bin/env ruby

require 'set'
require 'ostruct'

def day9(f)
  File.readlines(f).map do |x|
    el = x.chomp.split(' ')
    OpenStruct.new(direction: el.first, distance: el.last.to_i)
  end
end


def part1(f)
  game = day9(f)
  
  visited = Set.new()
  
  head = OpenStruct.new(x: 0, y: 0)
  tail = OpenStruct.new(x: 0, y: 0)
  
  visited << tail.dup

  game.each do |turn|
    if turn.direction == 'L'
      move = lambda { |x| x.x = x.x - 1 }
    elsif turn.direction == 'R'
      move = lambda { |x| x.x = x.x + 1 }
    elsif turn.direction == 'U'
      move = lambda { |x| x.y = x.y + 1 }
    elsif turn.direction == 'D'
      move = lambda { |x| x.y = x.y - 1 }
    end
    
    turn.distance.times do
      move.call(head)
      dx = head.x - tail.x
      dy = head.y - tail.y
      if    dy == 0 and dx.abs == 2
        tail.x = tail.x + dx / 2
      elsif dx == 0 and dy.abs == 2
        tail.y = tail.y + dy / 2
      elsif dx.abs == 1 and dy.abs == 2
        tail.x = head.x
        tail.y = tail.y + (dy <=> 0)
      elsif dx.abs == 2 and dy.abs == 1
        tail.x = tail.x + (dx <=> 0)
        tail.y = head.y
      end
      visited << tail.dup
    end
  end
  puts visited.size
end

def part2(f)
  grid = day9(f)
            

end

puts "Part 1"
part1("sample.txt")
part1("input.txt")

# puts "Part 2"
# part2("sample.txt")
# part2("input.txt")