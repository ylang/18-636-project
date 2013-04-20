EXEC = energyMP
BIBLIO = references
LATEX = pdflatex
#DVIPDF = dvipdf
BIBTEX = bibtex

all: $(EXEC)

$(EXEC): 
	$(LATEX) $(EXEC).tex
#	$(DVIPDF) $(EXEC).dvi

$(BIBLIO):
	$(LATEX) $(EXEC).tex
	$(LATEX) $(EXEC).tex
	$(BIBTEX) $(EXEC).aux

clean:
	-rm -f *.aux *.dvi *.log *.pdf  *.out

distclean:
	-rm -f *.aux *.dvi *.log *.blg *.bbl *.pdf  *.out
	
