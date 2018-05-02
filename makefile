.PHONY: 	CLEAN

.DEFAULT_GOAL := main.pdf

clean:
	-rm -f main.out main.toc main.aux

# I know, I know
main.pdf: clean
	pdflatex main.tex; pdflatex main.tex; firefox-developer-edition main.pdf

