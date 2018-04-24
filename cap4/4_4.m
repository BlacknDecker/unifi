% funzione esatta
f = @(x) sin(x);
f1 = @(x) cos(x);
interval = [0, 2*pi];

% polinomi interpolanti
xi = [0, pi, 2*pi];
fi = [f(0), f(pi), f(2*pi)];
f1i = [f1(0), f1(pi), f1(2*pi)];

pl = @(x) lagrange(xi, fi, x);
pn = @(x) newton(xi, fi, x);
ph = @(x) hermite(xi, fi, f1i, x);

% plots
subplot(4,1,1)
fplot(f, interval)
grid on
title('f(x) = sin(x)')
ax = gca;
ax.XTick = 0:pi/2:2*pi;
ax.XTickLabel = {'0','\pi/2','\pi','3\pi/2','2\pi'};

subplot(4,1,2)
fplot(pl, interval)
grid on
title('p(x) Lagrange')
ax = gca;
ax.XTick = 0:pi/2:2*pi;
ax.XTickLabel = {'0','\pi/2','\pi','3\pi/2','2\pi'};

subplot(4,1,3)
fplot(pn, interval)
grid on
title('p(x) Newton')
ax = gca;
ax.XTick = 0:pi/2:2*pi;
ax.XTickLabel = {'0','\pi/2','\pi','3\pi/2','2\pi'};

subplot(4,1,4)
fplot(ph, interval)
grid on
title('p(x) Hermite')
ax = gca;
ax.XTick = 0:pi/2:2*pi;
ax.XTickLabel = {'0','\pi/2','\pi','3\pi/2','2\pi'};

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

%%da testare!!! scrivi laroba sopra!
function y = hermite(xi, fi, f1i, x)

    if length(xi) ~= length(fi) || length(f1i) ~= length(fi)
        error('xi, fi e f1x hanno lunghezza diversa!')
    end

    % combino opportunamente i vettori
    xih = zeros(length(xi)*2, 1);
    fih = zeros(length(fi)*2, 1);

    for i = 1:length(xi)
        xih(i+i-1) = xi(i);
        xih(i+i) = xi(i);
        fih(i+i-1) = fi(i);
        fih(i+i) = f1i(i);
    end

    dd = diff_div_herm(xih, fih);
    disp(dd)

    y = dd(1);
    for i = 2 : length(dd)
        prod = dd(i);
        for j=1:i-1
            prod = prod*(x-xih(j));
        end
        y = y + prod;
    end
  disp(y)
end

% input:
%   fi - vettore con f(x0), f'(x0), ..., f(xn), f'(xn)
%   xi - vettore delle ascisse
% output:
%    fi - vettore riscrito con le differenze divise
function [f] = diff_div_herm(x, f)

    for i = length(x)-1 : -2 : 3
        f(i) = (f(i)-f(i-2))/(x(i)-x(i-2));
    end

    for i = 2 : length(x)-1
        for j = length(x) : -1 : i+1
            f(j) = (f(j)-f(j-1))/(x(j)-x(j-i));
        end
    end
end
