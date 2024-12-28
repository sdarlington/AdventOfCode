package day14

import (
	"aoc2024/helper"
	"strconv"
	"log"
	"regexp"
)

type coord struct {
	x,y int
}

type velocity struct {
	dx,dy int
}

type robot struct {
	location coord
	direction velocity
}

func parseInput(filename string) (output []robot) {
	regex := regexp.MustCompile("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)")
	output = helper.ParseInput(filename, func(each_line string) (starting_points robot) {
		// p=0,4 v=3,-3

		cols := regex.FindAllStringSubmatch(each_line, -1)

		if len(cols) == 1 && len(cols[0]) == 5 {
			x,_ := strconv.Atoi(cols[0][1])
			y,_ := strconv.Atoi(cols[0][2])
			dx,_ := strconv.Atoi(cols[0][3])
			dy,_ := strconv.Atoi(cols[0][4])
		

			starting_points = robot{coord{x,y}, velocity{dx,dy}}
		} else {
			log.Fatal("Couldn't parse line: ", each_line)
		}
		return
	})

	return
}

func next(input robot, gridX, gridY int) (result robot) {
	x := (input.location.x + input.direction.dx) % gridX
	y := (input.location.y + input.direction.dy) % gridY
	if x < 0 {
		x = x + gridX
	}
	if y < 0 {
		y = y + gridY
	}
	result = robot{coord{x,y}, input.direction}

	return
}

func Part1(inputFilename string) (result int) {
    starting_numbers := parseInput(inputFilename)

	//maxX,maxY := 11,7
	maxX,maxY := 101,103

	out := make([]robot, len(starting_numbers))
	for i,n := range starting_numbers {
		r := n
		for t := 0; t < 100; t++ {
			r = next(r, maxX, maxY)
		}
		out[i] = r
	}
	//log.Println(out)

	quad := make([]int, 4)
	for _,n := range out {
		if n.location.x < maxX / 2 {
			if n.location.y < maxY / 2 {
				quad[0]++
			} else if n.location.y > maxY / 2 {
				quad[1]++
			}
		} else if n.location.x > maxX / 2 {
			if n.location.y < maxY / 2 {
				quad[2]++
			} else if n.location.y > maxY /2 {
				quad[3]++
			}
		}
	}

	//log.Println(quad)
	result = quad[0] * quad[1] * quad[2] * quad[3]

	//log.Println(result)
    return
}

