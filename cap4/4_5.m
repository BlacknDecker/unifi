% esempio con f(x) = pi^x
xi = [0,1,2];
fi = [1, 3.14, 9.87];

y = spline3(xi, fi, 1.5, false);

function y = spline3(xi, fi, x, tipo)
    % trova quale segmento della spline valutare
    n = 0;
    for i = 1:xi(length(xi))
        if x > xi(i) && x <= xi(i+1)
            n = i;
            break
        end
    end

    s = Ex_4_19(xi, fi, tipo);
    y = double(subs(s(n), x));
end

% [ s ] = Ex_4_19(x, f, type)
% Algoritmo per la determinazione degli n polinomi che formano una spline
% cubica di tipo naturale (type = false) o not a knot (type = true).
% Input:
% - x: vettore delle ascisse d'interpolazione
% - f: vettore dei valori assunti nelle ascisse d'interpolazione
% - type: true se la spline e' di tipo not a knot, false se
% e' di tipo naturale
% Output:
% - s: il vettore contenente le n espressioni dei polinomi costituenti
% la spline

function [ s ] = Ex_4_19(x, f, type)
    n = length(x) - 1;
    xi = zeros(1, n - 1);
    phi = zeros(1, n - 1);
    for i = 1 : n - 1
        phi(i) = ( x(i + 1) - x(i) ) / ( x(i + 2) - x(i) );
        xi(i) = ( x(i + 2) - x(i + 1) ) / ( x(i + 2) - x(i) );
    end
    dd = Ex_4_16_2(x, f);
    if type
        m = Ex_4_17(phi, xi, dd);
    else
        m = Ex_4_16_1(phi, xi, dd);
    end
    s = Ex_4_18(x, f, m);
end



% [ f ] = Ex_4_16_2(x, f)
% Calcolo del vettore delle differenze divise per la risoluzione del
% sistema tridiagonale lineare delle spline cubiche.
% Input:
% - x: vettore delle ascisse d'interpolazione
% - f: vettore dei valori assunti dalla funzione in tali ascisse
% Output:
% - f: vettore contenente i valori delle differenze divise per il calcolo
% del sistema lineare tridiagonale per le spline cubiche
function[ f ] = Ex_4_16_2(x, f)
    n = length(x) - 1;
    for j = 1 : 2
        for i = n + 1 : - 1 : j + 1
            f(i) = ( f(i) - f(i - 1) )/(x(i) - x(i - j) );
        end
    end
    f = f(3 : length(f))';
end


% [ m ] = Ex_4_16_1(phi, xi, dd)
% Algoritmo per il calcolo del vettore m del sistema lineare tridiagonale
% per determinare i fattori m(i) nell'espressione della spline cubica
% naturale.
% Input:
% - phi: vettore dei fattori phi sulla matrice dei coefficienti (len = n - 1)
% - xi: vettore dei fattori xi sulla matrice dei coefficienti (len = n - 1)
% - dd: vettore delle differenze divise (len = n - 1)
% Output:
% - m: vettore contenente le m(i) calcolate (len = n + 1)
function [ m ] = Ex_4_16_1(phi, xi, dd)
    n = length(xi) + 1;
    u = zeros(1, n - 1);
    l = zeros(1, n - 2);
    u(1) = 2;
    for i = 2 : n - 1
        l(i) = phi(i) / u(i - 1);
        u(i) = 2 - l(i) * xi(i - 1);
    end
    dd = 6 * dd;
    y = zeros(1, n - 1);
    y(1) = dd(1);
    for i = 2 : n - 1
        y(i) = dd(i) - l(i) * y(i - 1);
    end
    m = zeros(1, n - 1);
    m(n - 1) = y(n - 1) / u(n - 1);
    for i = n - 2 : - 1 : 1
        m(i) = (y(i) - xi(i) * m(i + 1)) / u(i);
    end
    m = [0 m 0]; % condizioni della spline naturale: m(0) = m(n) = 0
end


% [ m ] = Ex_4_17(phi, xi, dd)
% Algoritmo per il calcolo del vettore m del sistema lineare tridiagonale
% per determinare i fattori m(i) nell'espressione della spline cubica
% not a knot.
% Input:
% - phi: vettore dei fattori phi sulla matrice dei coefficienti (len = n - 1)
% - xi: vettore dei fattori xi sulla matrice dei coefficienti (len = n - 1)
% - dd: vettore delle differenze divise (len = n - 1)
% Output:
% - m: vettore contenente le m(i) calcolate (len = n + 1)
function [ m ] = Ex_4_17( phi, xi, dd )
    n = length(xi) + 1;
    if n + 1 < 4
        error('ATTENZIONE: il numero di ascisse interpolanti è inferiore a 4, questo significa che la spline not a knot interpolante la funzione coincide con la funzione stessa');
    end
    dd = [6 * dd(1); 6 * dd; 6 * dd(length(dd))];
    w = zeros(n, 1);
    u = zeros(n + 1, 1);
    l = zeros(n, 1);
    y = zeros(n + 1, 1);
    m = zeros(n + 1, 1);
    u(1) = 1;
    w(1) = 0;
    l(1) = phi(1);
    u(2) = 2 - phi(1);
    w(2) = xi(1) - phi(1);
    l(2) = phi(2) / u(2);
    u(3) = 2 - ( l(2) * w(2) );
    w(3) = xi(2);
    for i = 4 : n - 1
        l(i - 1) = phi(i - 1) / u(i - 1);
        u(i) = 2 - l(i - 1) * w(i - 1);
        w(i) = xi(i - 1);
    end
    l(n - 1) = ( phi(n - 1) - xi(n - 1) ) / u(n - 1);
    u(n) = 2 - xi(n - 1) - l(n - 1) * w(n - 1);
    w(n) = xi(n - 1);
    l(n) = 0;
    u(n + 1) = 1;
    y(1) = dd(1);
    for i = 2 : n + 1
        y(i) = dd(i) - l(i - 1) * y(i - 1);
    end
    m(n + 1) = y(n + 1) / u(n + 1);
    for i = n : -1 : 1
        m(i) = (y(i) - w(i) * m(i + 1))/u(i);
    end
    m(1) = m(1) - m(2) - m(3);
    m(n + 1) = m(n + 1) - m(n) - m(n - 1);
end

% [ s ] = Ex_4_18(x, f, m)
% Algoritmo per la determinazione dell'espressione, polinomiale a tratti,
% della spline cubica.
% Input:
% - x: vettore delle ascisse d'interpolazione
% - f: vettore dei valori assunti nelle ascisse d'interpolazione
% - m: vettore delle m
% Output:
% - s: vettore dei polinomi costituenti la spline polinomiale a tratti
function [ s ] = Ex_4_18( xi, fi, m)
    n = length(xi) - 1;
    s = sym('x' , [n 1]);
    syms x;
    for i = 2 : n + 1
        hi = xi(i) - xi(i - 1);
        ri = fi(i - 1) - hi^2/6 * m(i - 1);
        qi = (fi(i) - fi(i - 1))/hi - hi/6 * (m(i) - m(i - 1));
        s(i - 1) = ( (x - xi(i - 1))^3 * m(i) + (xi(i) - x)^3 * m(i - 1) ) / (6 * hi) + qi * (x - xi(i - 1)) + ri;
    end
end
