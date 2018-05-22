.PHONY: 	clean
.PHONY:		view

.DEFAULT_GOAL := all

all: elab prog

elab:
	pdflatex main.tex;

prog:
	pdflatex progr/prog.tex
