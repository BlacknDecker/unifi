.PHONY: 	clean
.PHONY:		view

.DEFAULT_GOAL := 	view

clean:
	-rm -f main.out main.toc main.aux

# I know, I know
pdf: clean
	pdflatex main.tex; pdflatex main.tex;
	
view: pdf
	firefox-developer-edition main.pdf

prog:
	pdflatex programma/prog.tex

