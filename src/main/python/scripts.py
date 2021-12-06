def clearBreakLines(clearedFilePath, newFilePath):
    new_file = open(newFilePath, mode="a+", encoding="utf-8")
    with open("clearedFilePath", encoding='utf-8') as file:
        for line in file:
            if line.count(',') == 8:
                new_file.write(line.replace("\n", ""))
            else:
                new_file.write(line)
    new_file.close()





