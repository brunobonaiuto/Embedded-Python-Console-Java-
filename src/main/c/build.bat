cd src\main\c
del a.exe

gcc -Os -s main.c -I"C:\Users\bbbolivar\AppData\Local\Programs\Python\Python312\include" -L"C:\Users\bbbolivar\AppData\Local\Programs\Python\Python312" -lpython312

.\a.exe