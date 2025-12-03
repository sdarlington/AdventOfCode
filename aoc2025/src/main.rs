
mod helper;
mod day01;
mod day02;
mod day03;

fn main() {
    println!("Day 1");
    println!("Part 1: {}", day01::part1("input-01.txt"));
    println!("Part 2: {}", day01::part2("input-01.txt"));
    
    println!("---");
    
    println!("Day 2");
    println!("Part 1: {}", day02::part1("input-02.txt"));
    println!("Part 2: {}", day02::part2("input-02.txt"));

    println!("---");
    
    println!("Day 3");
    println!("Part 1: {}", day03::part1("input-03.txt"));
    println!("Part 2: {}", day03::part2("input-03.txt"));
}
