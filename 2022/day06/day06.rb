#!/usr/bin/env ruby

require 'set'

def day6(f)
  File.read(f)
      .split
end

def part1(f, m = 4)
  streams = day6(f)
  
  streams.each do |stream|
    chars = stream.split('')
    
    posn = 0
    while chars.size >= m
      previous = Set.new(chars[0...m])
      break if previous.size == m
      chars.shift
      posn = posn + 1
    end
    puts posn + m
  end
end

puts "Part 1"
part1("sample.txt")
part1("input.txt")

puts "Part 2"
part1("sample2.txt", 14)
part1("input.txt", 14)