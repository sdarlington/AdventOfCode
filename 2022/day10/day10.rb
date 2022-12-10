#!/usr/bin/env ruby

require 'set'
require 'ostruct'

def day10(f)
  File.readlines(f).map do |x|
    el = x.chomp.split(' ')
    OpenStruct.new(instruction: el.first, operand: el.last.to_i)
  end
end


def part1(f)
  game = day10(f)
  
  strength = []
  interesting_cycles = [20,60,100,140,180,220]
  cycle = 0
  x = 1
  game.each do |op|
    if op.instruction == 'noop'
      cycle = cycle + 1
      if interesting_cycles.include?(cycle)
        strength.append(cycle * x)
      end
    elsif op.instruction == 'addx'
      if interesting_cycles.include?(cycle + 1)
        strength.append((cycle + 1) * x)
      elsif interesting_cycles.include?(cycle + 2)
        strength.append((cycle + 2) * x)
      end
      cycle = cycle + 2
      x = x + op.operand
    end
  end
  puts strength.sum
end

def part2(f)
  game = day10(f)
  
end

puts "Part 1"
part1("sample.txt")
part1("input.txt")

# puts "Part 2"
# part2("sample.txt")
# part2("input.txt")