extern crate tokenizers as tk;

use ::safer_ffi::prelude::*;

use tk::Tokenizer;
use tk::FromPretrainedParameters;

#[derive_ReprC]
#[ReprC::opaque]
pub struct FFITokenizer {
    tokenizer: Tokenizer
}

#[ffi_export]
fn tokenizer_new() -> repr_c::Box<FFITokenizer>
{
    let identifier = "setu4993/LaBSE";
    let parameters = FromPretrainedParameters::default();
    let tk_result = Tokenizer::from_pretrained(identifier, Some(parameters));
    match tk_result {
        Err(_e) => panic!("identifier not found"),
        Ok(v) => repr_c::Box::new(FFITokenizer { tokenizer: v }),
    }
}

#[ffi_export]
fn tokenizer_encode(it: &FFITokenizer) //-> repr_c::Box<FFIEncoding>
//fn tokenizer_encode(it: &FFITokenizer, input: repr_c::String) -> repr_c::Box<FFIEncoding>
{
    let input = "hello I'm viet";
    let encoded = it.tokenizer.encode(input, false);
    match encoded {
        Err(_e) => panic!("encode failed"),
        Ok(encoding) => {
            let ids = encoding.get_ids().iter().map(|i|i64::from(*i)).collect::<Vec<_>>().into();
            let type_ids = encoding.get_type_ids().iter().map(|i|i64::from(*i)).collect::<Vec<_>>().into();
            // let foo: Vec<repr_c::String> = encoding.get_tokens().into_iter().map(|s| repr_c::String::from(s.clone())).rev().collect();
            let words = encoding.get_word_ids().iter()
                .map(|w| match w {
                    Some(v) => i64::from(*v),
                    None => -1, // to indicate null
                })
                .collect::<Vec<_>>().into();

            repr_c::Box::new(FFIEncoding { ids, type_ids, words });
        },
    }
}

#[ffi_export]
fn tokenizer_drop(ptr: repr_c::Box<FFITokenizer>)
{
    drop(ptr);
}

#[derive_ReprC]
#[repr(C)]
pub struct FFIEncoding {
    ids: repr_c::Vec<i64>,
    type_ids: repr_c::Vec<i64>,
    // tokens: c_slice::Box<repr_c::String>,
    words: repr_c::Vec<i64>,
}

#[ffi_export]
fn encoding_drop(ptr: repr_c::Box<FFIEncoding>) 
{
    drop(ptr);
}

/// The following test function is necessary for the header generation. 
/// Headers are only needed during development. It helps inspecting the 
/// needed JNA interface.
#[::safer_ffi::cfg_headers]
#[test]
fn generate_headers () -> ::std::io::Result<()>
{
    ::safer_ffi::headers::builder()
        .to_file("tokenizers.h")?
        .generate()
}
