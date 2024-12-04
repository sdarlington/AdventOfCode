package day04

import (
	"aoc2024/helper"
	"strings"
)

type puzzle struct {
	Grid []string
	xMax,yMax int
}

func (p puzzle) word (x,y,dx,dy,l int) string {
	var out strings.Builder
    for c := 0; c < l; c++ {
        chr := []byte { p.Grid[y][x] }
    	out.Write( chr )
    	x += dx
    	y += dy
    	if x < 0 || x >= p.xMax || y < 0 || y >= p.yMax {
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
  for x := 0; x < puzzle.xMax; x++ {
  	for y := 0; y < puzzle.yMax; y++ {
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

func Part2(inputFilename string) (result int) {
  puzzle := parseInput(inputFilename)

  result = 0
  for x := 1; x < puzzle.xMax - 1; x++ {
  	for y := 1; y < puzzle.yMax - 1; y++ {
  	  words := []string {
  	     puzzle.word(x-1,y-1,1,1,3), //top left - bottom right
  	     puzzle.word(x-1,y+1,1,-1,3), //bottom left - top right
  	     puzzle.word(x+1,y+1,-1,-1,3), //bottom-right - top left
  	     puzzle.word(x+1,y-1,-1,1,3), //top-right - bottom left
  	     }
  	  if (words[0] == "MAS" || words[2] == "MAS") &&
  	     (words[1] == "MAS" || words[3] == "MAS") {
  	     result++
  	  }
  	}
  }

  return
}
