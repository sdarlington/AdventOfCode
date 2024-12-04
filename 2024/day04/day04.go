package day04

import (
	"aoc2024/helper"
	"strings"
)

type puzzle struct {
	Grid []string
	x,y int
}

func (p puzzle) word (x,y,dx,dy,l int) string {
	var out strings.Builder
    for c := 0; c < l; c++ {
        chr := string(p.Grid[y][x])
    	out.WriteString( chr )
    	x += dx
    	y += dy
    	if x < 0 || x >= p.x {
    	  break
    	}
    	if y < 0 || y >= p.y {
    	  break
    	}
    }
    return out.String()
}

func parseInput(filename string) (output puzzle) {
	grid := helper.ParseInput(filename, func(each_line string) (reports string) {
		reports = each_line
		return
	})

	output = puzzle{grid, len(grid[0]), len(grid)}
	return
}



func Part1(inputFilename string) (result int) {
  puzzle := parseInput(inputFilename)

  result = 0
  for x := 0; x < puzzle.x; x++ {
  	for y := 0; y < puzzle.y; y++ {
  	  words := []string {
  	     puzzle.word(x,y,1,0,4), // right
  	     puzzle.word(x,y,-1,0,4), // left
  	     puzzle.word(x,y,0,1,4), // down
  	     puzzle.word(x,y,0,-1,4), //up
  	     puzzle.word(x,y,1,-1,4), //up-right
  	     puzzle.word(x,y,-1,-1,4), //up-left
  	     puzzle.word(x,y,1,1,4), //down-right
  	     puzzle.word(x,y,-1,1,4), //down-left
  	     }
  	  for _,w := range words {
  	    if w == "XMAS" {
  	      result++
  	    }
  	  }
  	}
  }

  return
}
