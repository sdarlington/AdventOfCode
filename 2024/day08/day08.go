package day08

import (
	"aoc2024/helper"
// 	"strings"
    "log"
    "fmt"
)

type coord struct {
  x, y int
}

type plan struct {
	Grid []string
	xMax,yMax int
}

func (p plan) cell (xy coord) string {
    return string(p.Grid[xy.y][xy.x])
}

func parseInput(filename string) (output plan) {
	input := helper.ParseInput(filename, func(each_line string) (reports string) {
		reports = each_line
		return
	})
	
	output.Grid = input
	output.xMax = len(input[0])
	output.yMax = len(input)

	return
}

func Part1(inputFilename string) (result int) {
  output := parseInput(inputFilename)

  // Preprocess
  grid := make(map[coord]string)
  antenna := make(map[string][]coord)
  for y := 0; y < output.yMax; y++ {
      for x := 0; x < output.xMax; x++ {
          location := coord{x,y}
          cell := output.cell(location)
          if cell != "." {
              grid[location] = cell
              antennaList, ok := antenna[cell]
              if ok {
                  antennaList = append(antennaList, location)
              } else {
                  antennaList = []coord{location}
              }
              antenna[cell] = antennaList
          }
      }
  }
  
  log.Println(grid)
  log.Println(antenna)
  
  antinodes := make(map[coord]bool)
  for freq,nodes := range antenna {
      log.Println("F:", freq, "Nodes:", nodes)
       for l1 := 0; l1 < len(nodes) -1; l1++ {
           for l2 := l1 + 1; l2 < len(nodes); l2++ {
               n1 := nodes[l1]
               n2 := nodes[l2]
               log.Println(n1, n2)
               dx, dy := n2.x - n1.x, n2.y - n1.y
               an1 := coord{n1.x - dx, n1.y - dy}
               an2 := coord{n2.x + dx, n2.y + dy}
               log.Println("1: ", an1, " 2: ", an2)
               if an1.x >=0 && an1.x < output.xMax && an1.y >= 0 && an1.y < output.yMax {
                 log.Println("+1")
                 antinodes[an1] = true
               }
               if an2.x >=0 && an2.x < output.xMax && an2.y >= 0 && an2.y < output.yMax {
                 log.Println("+1")
                 antinodes[an2] = true
               }
           }
       }
  }
  result = len(antinodes)

  return
}

func (p plan)print(antinodes map[coord]bool) {
    for y := 0; y < p.yMax; y++ {
        for x := 0; x < p.xMax; x++ {
            _, ok := antinodes[coord{x,y}]
            if ok {
                fmt.Print("#")
            } else if p.cell(coord{x,y}) != "." {
                fmt.Print(p.cell(coord{x,y}))
            } else {
                fmt.Print(".")
            }
        }
        fmt.Println("")
    }
}

func Part2(inputFilename string) (result int) {
  output := parseInput(inputFilename)

  // Preprocess
  grid := make(map[coord]string)
  antenna := make(map[string][]coord)
  for y := 0; y < output.yMax; y++ {
      for x := 0; x < output.xMax; x++ {
          location := coord{x,y}
          cell := output.cell(location)
          if cell != "." {
              grid[location] = cell
              antennaList, ok := antenna[cell]
              if ok {
                  antennaList = append(antennaList, location)
              } else {
                  antennaList = []coord{location}
              }
              antenna[cell] = antennaList
          }
      }
  }
  
  log.Println(grid)
  log.Println(antenna)
  
  antinodes := make(map[coord]bool)
  for freq,nodes := range antenna {
      log.Println("F:", freq, "Nodes:", nodes)
       for l1 := 0; l1 < len(nodes) -1; l1++ {
           for l2 := l1 + 1; l2 < len(nodes); l2++ {
               n1 := nodes[l1]
               n2 := nodes[l2]
               antinodes[n1] = true
               antinodes[n2] = true
               log.Println(n1, n2)
               dx, dy := n2.x - n1.x, n2.y - n1.y
               
               if dx == 0 && dy == 0 {
                   log.Fatal("Zeros!")
               }
               an1 := n1
               for {
                   log.Println("Before: ", an1)
                   an1 = coord{an1.x - dx, an1.y - dy}
                   log.Println("After: ", an1)
                   if an1.x >=0 && an1.x < output.xMax && an1.y >= 0 && an1.y < output.yMax {
                       log.Println("+1")
                       antinodes[an1] = true
                   } else {
                       log.Println("boom")
                       break
                   }
               }

               an2 := n2
               for {
                   an2 = coord{an2.x + dx, an2.y + dy}
                   log.Println(an2)
                   if an2.x >=0 && an2.x < output.xMax && an2.y >= 0 && an2.y < output.yMax {
                       log.Println("+1")
                       antinodes[an2] = true
                   } else {
                       break
                   }
               }
               
           }
       }
  }
  output.print(antinodes)
  result = len(antinodes)

  return
}