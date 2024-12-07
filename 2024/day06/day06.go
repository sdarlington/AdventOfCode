package day06

import (
	"aoc2024/helper"
// 	"log"
	"strings"
)

type coord struct {
  x, y int
}

type puzzle struct {
	Grid []string
	guard coord
	visited map[coord]bool
	xMax,yMax int
}

type direction struct {
    dx, dy int
}

func (c coord) next (d direction) coord {
    return coord{c.x + d.dx, c.y + d.dy}
}

func (p puzzle) findGuard() coord {
    for i, line := range p.Grid {
        idx := strings.Index(line, "^")
        if idx > -1 {
            return coord{idx, i}
        }
    }
    return coord{-1,-1}
}

func (p puzzle) cell (xy coord) string {
    return string(p.Grid[xy.y][xy.x])
}

func parseInput(filename string) (output puzzle) {
	grid := helper.ParseInput(filename, func(each_line string) (reports string) {
		reports = each_line
		return
	})

	output = puzzle{grid, coord{0,0}, make(map[coord]bool), len(grid[0]), len(grid)}
	output.guard = output.findGuard()

	return
}

func Part1(inputFilename string) (result int) {
  puzzle := parseInput(inputFilename)

  d := direction{0,-1}
  for {
//       log.Println("** ", puzzle.guard, " ", puzzle.cell(puzzle.guard))
      next := puzzle.guard.next(d)
//       log.Println(next)
      if next.x >= puzzle.xMax || next.y >= puzzle.yMax || next.x < 0 || next.y < 0 {
//         log.Println("Quittin' ", next)
        break
      }
      cell := puzzle.cell(next)
//       log.Println("Next cell: ", cell)
      if cell == "." || cell == "^" {
//           log.Println("Cell ok")
          puzzle.guard = next
          puzzle.visited[next] = true
//           log.Println (puzzle.visited)
      } else {
//           log.Println("Cell not ok")
          switch d {
          case direction{0,-1}:
            d = direction{1,0}
          case direction{1,0}:
            d = direction{0,1}
          case direction{0,1}:
            d = direction{-1,0}
          case direction{-1,0}:
            d = direction{0,-1}
          }
//           log.Println("Next d: ", d)
      }
  }

  return len(puzzle.visited)
}
