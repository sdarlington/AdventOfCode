

use std::fs::File;
use std::io::{self, BufRead, BufReader};

pub fn parse_input<T, F>(filename: &str, parser: F) -> io::Result<Vec<T>>
where
    F: Fn(&str) -> T,
{
    let file = File::open(filename)?;
    let reader = BufReader::new(file);

    let mut result = Vec::new();

    for line in reader.lines() {
        let line = line?;              // handle IO errors
        result.push(parser(&line));    // apply parser function
    }

    Ok(result)
}
