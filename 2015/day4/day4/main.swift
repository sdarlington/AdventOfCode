//
//  main.swift
//  day4
//
//  Created by Stephen Darlington on 20/12/2015.
//  Copyright © 2015 Wandle Software Limited. All rights reserved.
//

import Foundation

let arguments = NSProcessInfo.processInfo().arguments
guard arguments.count == 2 else {
    print("MD5 is the first parameter")
    exit(1)
}

//let text = arguments[1]
//print(text, " = ", text.MD5())

var c = 0
var md5hex : String
repeat {
    c++
    let passcode = arguments[1] + String(c)
    md5hex = passcode.MD5()
} while !md5hex.hasPrefix("000000")

print("Hash: ", md5hex, "\nCount: ", c)