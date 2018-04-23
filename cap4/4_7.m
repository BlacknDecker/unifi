runge = @(x) 1/(1+x^2);
a = -6;
b = 6;

fplot(runge, [a, b])
print('-dpng','runge.png')

n = 2;
while n<=40
    xi = ceby(n, a, b);
    fi = zeros(length(xi), 1);

    for i = 1:length(xi)
        fi(i) = feval(runge, xi(i));
    end

    p = @(x) lagrange(xi, fi, x);
    fplot(p, [a, b])
    print('-dpng', strcat(num2str(n), '.png'));

    n = n + 2;
end

%
% input:
%   n - numero di ascisse da cercare
%   a - punto iniziale dell'intervallo
%   b - punto finale dell'intervallo
% output:
%   xi - vettore con le ascisse di Chebyshev
%
function xi = ceby(n, a, b)
	xi = zeros(n+1, 1);
		for i = 0:n
			xi(n+1-i) = (a+b)/2 + cos(pi*(2*i+1)/(2*(n+1)))*(b-a)/2;
		end
end

%
% input:
%   xi - vettore dei punti di ascissa
%   fi - vettore dei valori di f(x)
%   x  - punto in cui calcolare f(x)
% output:
%   y  - valore di f(x)
%
function y = lagrange(xi, fi, x)
    if length(xi) ~= length(fi)
        error('x_i e f(x_i) hanno lunghezza diversa')
    end
    y = 0;
    for i = 1:length(xi)
        y = y + fi(i)*l_k_n(x, xi, i);
    end
end

function lkn = l_k_n(x, xi, k)
    lkn = 1;
    for i = 1:length(xi)
        if i ~= k
            lkn = lkn * (x-xi(i))/(xi(k)-xi(i));
        end
    end
end
