
#[derive(PartialEq)]
enum ParseInput {
  Add,
  Multiply,
  Number (i64)
}

fn parse_input(filename : &str) -> Vec<Vec<ParseInput>> {
    let cols = crate::helper::parse_input(filename, |line| {
        return line.split_ascii_whitespace()
                   .map(|x| match x {
                     "+" => ParseInput::Add,
                     "*" => ParseInput::Multiply,
                     _ => ParseInput::Number(x.parse::<i64>().unwrap())
                   })
                   .collect();
    }).unwrap();
    
    return cols;
}

pub fn part1(filename : &str) -> i64 {
    let input = parse_input(filename);
    
    let mut total : i64 = 0;
    for (idx, op) in input.last().unwrap().iter().enumerate() {
        let mut running : Option<i64> = None;
        for r in &input {
            let col = &r[idx];
            match col {
              ParseInput::Number(x) => {
				running = match running {
				  None => Some(*x),
				  Some(t) => match op { ParseInput::Add => Some(t+x), ParseInput::Multiply => Some(t*x), _ => Some(t) }
				}
              },
              _ => ()
            }
        }
        match running {
          Some(n) => total += n,
          _ => ()
        }
    }
    
    return total;
}

#[cfg(test)]
mod tests {

    use super::*;
    
    #[test]
    fn test_part1() {
        let r = part1("sample-06.txt");
        assert_eq!(r, 4277556);
    }
    
    #[test]
    fn test_part2() {
        let r = part2("sample-06.txt");
        assert_eq!(r, 14);
    }

}

pub fn part2(filename : &str) -> i64 {
    let input = parse_input(filename);
    
    let mut total : i64 = 1;

    return total;
}