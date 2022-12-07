#!/usr/bin/env ruby

def day7(f)
  File.readlines(f)
end
  
def process(game)
  directories = {}
  current_dir = nil
  
  game.each do |line|
    if line.match(/\$ cd/)
      line.match(/\$ cd (.*)/) do |m|
        dir = m.captures[0]
        if dir == '/'
          directories[['/']] = 0
          current_dir = ['/']
        elsif dir == '..'
          current_dir.pop
        else
          current_dir.append(dir)
          if not directories.value?(current_dir)
            directories[current_dir.dup] = 0
          end
        end
      end
    elsif line.match(/\$ ls/)
    elsif line.match(/dir/)
    else
      line.match(/([0-9]*) (.*)/) do |m|
        s = m.captures[0].to_i
        dirs = current_dir.dup
        while dirs.size > 0
          directories[dirs] = directories[dirs] + s
          dirs.pop
        end
      end
    end
  end
  directories
end

def part1(f)
  game = day7(f)
  
  directories = process(game)
  
  puts directories.map { |x| x.last }
                  .filter { |x| x < 100_000 }
                  .sum
end

def part2(f)
  game = day7(f)
  
  directories = process(game)
  
  free_space = 70_000_000 - directories[['/']]
  req_space = 30_000_000 - free_space

  puts directories.filter { |x| directories[x] > req_space}
                  .sort_by { |x| x.last }
                  .first
                  .last
end

puts "Part 1"
part1("sample.txt")
part1("input.txt")

puts "Part 2"
part2("sample.txt")
part2("input.txt")