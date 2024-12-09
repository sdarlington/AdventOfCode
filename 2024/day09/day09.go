package day09

import (
	"aoc2024/helper"
)

type disk_block struct {
    fileid int
}

func parseInput(filename string) (output [][]disk_block) {
	output = helper.ParseInput(filename, func(each_line string) (disk_map []disk_block) {
		fileid := 0
		for i,e := range each_line {
		    block := int(e - '0')
		    if i % 2 == 0 {
		        for b := 0; b < block; b++ {
		            disk_map = append(disk_map, disk_block{fileid})
		        }
		        fileid++
		    } else {
		        for b := 0; b < block; b++ {
		            disk_map = append(disk_map, disk_block{-1})
		        }
		    }
		}
		return
	})

	return
}

func nextFree(block []disk_block, free int) int {
    for i := free; i < len(block); i++ {
        if block[i].fileid == -1 {
            free = i
            break
        }
    }
    return free
}

func scoreBlocks(block []disk_block) int {
    score := 0
    for i,b := range block {
        if b.fileid != -1 {
            score += i * b.fileid
        }
    }
    return score
}

func Part1(inputFilename string) (result int) {
    blocks := parseInput(inputFilename)[0]

    free := nextFree(blocks, 0)
    
    for i := len(blocks) - 1; i > free; i-- {
        if blocks[i].fileid != -1 {
            blocks[free].fileid, blocks[i].fileid = blocks[i].fileid, -1
            free = nextFree(blocks, free)
        }
    }

    result = scoreBlocks(blocks)
    
    return
}
