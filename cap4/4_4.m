% plot della funzione esatta

f = @(x) sin(x);
int = [0, 2*pi];

fplot(f, int)
hold on

% plot polinomi interpolanti
xi = [0, pi, 2*pi];
fi = [f(0), f(pi), f(2*pi)];

p = @(x) newton(xi, fi, x);
fplot(p, int)

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

%
% input:
%   xi - vettore dei punti di ascissa
%   fi - vettore dei valori di f(x)
%   x  - punto in cui calcolare f(x)
% output:
%   y  - valore di f(x)
%
function y = newton(xi, fi, x)
    dd = diff_div(xi, fi);
    y = dd(length(dd));
    for k = length(dd)-1:-1:1
        y = y*(x-xi(k))+dd(k);
    end
end

function [fi] = diff_div(xi, fi)
    for i = 1:length(xi) - 1
        for j = length(xi):-1:i+1
            fi(j) = (fi(j) - fi(j-1))/(xi(j)-xi(j-i));
        end
    end
end
