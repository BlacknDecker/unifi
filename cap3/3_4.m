function [b]= risoluzioneSistemaLUpivoting(A,b)
	P = zeros(length(A));
	for i=1:length(A)
		P(i, p(i)) = 1;
	end
	b = sistemaTriangolareInferiore(tril(A,-1)+eye(length(A)), P*b);
	b = sistemaTriangolareSuperiore(triu(A), b);
end
