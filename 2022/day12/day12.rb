#!/usr/bin/env ruby

require 'set'
require 'ostruct'

def day12(f)
  File.readlines(f).map do |x|
    el = x.chomp.split(' ')
    OpenStruct.new(instruction: el.first, operand: el.last.to_i)
  end
end

def part1(f)
  game = day12(f)
end

def part2(f)
  game = day12(f)
end

puts "Part 1"
part1("sample.txt")
# part1("input.txt")

# puts "Part 2"
# part2("sample.txt")
# part2("input.txt")