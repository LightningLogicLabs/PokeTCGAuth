import os 

os.system("gradle installDist")
os.system("docker build -t poketcgauth .")