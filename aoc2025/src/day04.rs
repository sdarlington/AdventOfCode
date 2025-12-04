
enum Cell {
  Empty, Paper
}

struct PaperMap {
  map : Vec<Vec<Cell>>,
}

impl PaperMap {
    fn get(&self, x : usize, y : usize) -> &Cell {
        return &self.map[y][x];
    }
    
    fn adjacent(&self, x: usize, y : usize) -> i32 {
        let mut result : i32 = 0;
        let min_x = if x == 0 { 0 } else { x - 1 };
        let min_y = if y == 0 { 0 } else { y - 1 };
        for test_y in min_y ..= y + 1 {
            for test_x in min_x ..= x + 1 {
                if test_x == x && test_y == y {
                    continue;
                }
                if test_x > self.map[0].len() - 1 {
                    continue;
                }
                if test_y > self.map.len() - 1 {
                    continue;
                }
                match self.get(test_x, test_y) {
                Cell::Paper => result += 1,
                _ => ()
                }
            }
        }
        return result;
    }
}

fn parse_input(filename : &str) -> PaperMap {
    let cols = crate::helper::parse_input(filename, |line| {
        let elements = line.chars().map(|x| match x { '.' => Cell::Empty, '@' => Cell::Paper, _ => Cell::Empty }).collect();
        return elements;
    }).unwrap();
    
    return PaperMap { map : cols };
}

pub fn part1(filename : &str) -> i32 {
    let maps = parse_input(filename);
    
    let mut total : i32 = 0;
    for y in 0 .. maps.map.len() {
        for x in 0 .. maps.map[0].len() {
            match maps.get(x,y) {
                Cell::Empty => continue,
                Cell::Paper => ()
            }
            if maps.adjacent(x, y) < 4 {
                total += 1;
            }
        }
    }
    
    return total;
}

#[cfg(test)]
mod tests {

    use super::*;
    
    #[test]
    fn test_part1() {
        let r = part1("sample-04.txt");
        assert_eq!(r, 13);
    }
    
    #[test]
    fn test_part2() {
        let r = part2("sample-04.txt");
        assert_eq!(r, -1);
    }

}

pub fn part2(filename : &str) -> i64 {
    let batteries = parse_input(filename);
    
    let mut total : i64 = 0;

    return total;
}