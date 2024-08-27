cd src\main\c
del a.exe

::gcc -Os -s main.c -I"C:\Users\bbbolivar\AppData\Local\Programs\Python\Python312\include" -L"C:\Users\bbbolivar\AppData\Local\Programs\Python\Python312" -lpython312

gcc -Os -s practicing_stdout_stderr.c -I"C:\Users\bbbolivar\AppData\Local\Programs\Python\Python312\include" -L"C:\Users\bbbolivar\AppData\Local\Programs\Python\Python312" -lpython312

.\a.exe