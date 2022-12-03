#!/usr/bin/env ruby


def day1(f)
  elves = []
  current_elf = 0
  elf_num = 1
  File.foreach(f) { |each_line|
    if each_line == "\n"
      elves.append([elf_num, current_elf])
      elf_num = elf_num + 1
      current_elf = 0
    else
      current_elf += each_line.to_i
    end
  }
  elves.append( [elf_num, current_elf])
  
  return elves.sort_by { |x| x.last }
end

def part1(f)
  elves = day1(f)
  puts elves.last.last
end

def part2(f)
  elves = day1(f).reverse.take(3).map { |x| x.last }.sum
  puts elves
  
end

puts "Part 1"
part1("sample.txt")
part1("input.txt")

puts "Part 2"
part2("sample.txt")
part2("input.txt")