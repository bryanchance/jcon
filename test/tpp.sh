#!/bin/sh
#
#  special test of preprocessing and related error detection

../bin/jtran -E tpp.icn >tpp.out 2>tpp.err
set -e
cmp tpp.stdo tpp.out
cmp tpp.stde tpp.err
