
#[derive(PartialEq)]
#[derive(Debug)]
enum ParseInput {
  Add,
  Multiply,
  Blank,
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
        assert_eq!(r, 3263827);
    }

}

fn parse_input2(filename : &str) -> Vec<Vec<ParseInput>> {
    let cols = crate::helper::parse_input(filename, |line| {
        return line.chars().enumerate()
                   .map(|x| match x {
                     (_s,'+') => ParseInput::Add,
                     (_s,'*') => ParseInput::Multiply,
                     (_s,' ') => ParseInput::Blank,
                     (_s,x) => ParseInput::Number(x.to_string().parse::<i64>().unwrap())
                   })
                   .collect();
    }).unwrap();
    
    return cols;
}

pub fn part2(filename : &str) -> i64 {
    let input = parse_input2(filename);
    
    let mut total : i64 = 0;
    let mut numbers : Vec<i64> = Vec::new();
    let mut operation : Option<ParseInput> = None;
    for col in (0 .. input[0].len()).rev() {
        let mut num : i64 = 0;
        for y in &input {
            match y[col] {
                ParseInput::Number(x) => num = num * 10 + x,
                ParseInput::Blank => (),
                ParseInput::Add => operation = Some(ParseInput::Add),
                ParseInput::Multiply => operation = Some(ParseInput::Multiply)
            }
        }
        if num > 0 {
            numbers.push(num);
        }
        match &operation {
            Some(op) => match op {
                ParseInput::Add => {total += numbers.clone().into_iter().reduce(|x,y| x + y).unwrap(); operation = None; numbers.clear(); },
                ParseInput::Multiply => {total += numbers.clone().into_iter().reduce(|x,y| x * y).unwrap(); operation = None; numbers.clear(); },
                _ => {}
            },
            None => {}
        }
    }

    return total;
}