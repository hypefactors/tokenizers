extern crate tokenizers as tk;

use ::safer_ffi::prelude::*;

use tk::Tokenizer;
use tk::FromPretrainedParameters;

#[derive_ReprC]
#[ReprC::opaque]
pub struct FFITokenizer {
    tokenizer: Tokenizer
}

#[derive_ReprC]
#[repr(C)]
pub struct FFIEncoding {
    ids: repr_c::Vec<i64>,
    type_ids: repr_c::Vec<i64>,
    tokens: repr_c::Vec<char_p::Box>,
    words: repr_c::Vec<i64>,
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
fn tokenizer_encode(it: &FFITokenizer, ffi_input: char_p::Ref) -> repr_c::Box<FFIEncoding>
{
    let input = ffi_input.to_str();
    let encoded = it.tokenizer.encode(input, false);
    match encoded {
        Err(_e) => panic!("encode failed"),
        Ok(encoding) => {
            let ids = encoding.get_ids().iter().map(|i|i64::from(*i)).collect::<Vec<_>>().into();
            let type_ids = encoding.get_type_ids().iter().map(|i|i64::from(*i)).collect::<Vec<_>>().into();
            let tokens = encoding.get_tokens().iter().map(|s| char_p::new(s.clone())).collect::<Vec<_>>().into();
            let words = encoding.get_word_ids().iter()
                .map(|w| match w {
                    Some(v) => i64::from(*v),
                    None => -1, // to indicate null
                })
                .collect::<Vec<_>>().into();

            repr_c::Box::new(FFIEncoding { ids, type_ids, tokens, words })
        },
    }
}

#[ffi_export]
fn tokenizer_drop(ptr: repr_c::Box<FFITokenizer>)
{
    drop(ptr);
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
