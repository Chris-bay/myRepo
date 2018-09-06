import tkinter
# import tkMessageBox

top = tkinter.Tk()

C = tkinter.Canvas(top, bg="white", height=250, width=300)

coord = 10, 50, 240, 210
arc = C.create_arc(coord, start=0, extent=150, fill="red")
rect = C.create_rectangle()

C.pack()
top.mainloop()